import * as React from 'react'
import axios from 'axios'
import {IconButton, Stack} from '@mui/material'
import {EmailRounded, HomeRounded, LogoutRounded} from '@mui/icons-material'
import {ContentBoxWrap} from './ContentBoxes'
import {StringAvatar} from './StringAvatar'


export class Sidebar extends React.Component {
    constructor(props) {
        super(props)
        this.state = {name: null}
    }

    componentDidMount = () =>
        axios.get('/api/user', {withCredentials: true})
            .then(res => this.setState({name: res.data.user.name}))


    render = () => (
        <Stack gap={2} position={'fixed'}>
            <ContentBoxWrap padding={2}>
                <StringAvatar name={this.state.name}/>
            </ContentBoxWrap>
            <ContentBoxWrap padding={2}>
                <Stack gap={2}>
                    <IconButton href={'/home'} color='primary'>
                        <HomeRounded/>
                    </IconButton>
                    <IconButton href={'/rooms'} color='primary'>
                        <EmailRounded/>
                    </IconButton>
                </Stack>
            </ContentBoxWrap>
            <ContentBoxWrap padding={2}>
                <IconButton href={'/logout'} color='primary'>
                    <LogoutRounded/>
                </IconButton>
            </ContentBoxWrap>
        </Stack>
    )
}