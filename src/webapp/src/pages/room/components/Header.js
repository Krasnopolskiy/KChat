import * as React from 'react'
import axios from 'axios'
import {Box, IconButton, Stack, Typography} from '@mui/material'
import {ArrowBackRounded} from '@mui/icons-material'
import {ContentBoxFluid} from '../../../components/ContentBoxes'

export class Header extends React.Component {
    constructor(props) {
        super(props)
        this.state = {name: null}
    }

    componentDidMount = () => {
        const code = window.location.pathname.split('/').at(-1)
        axios.get(`/api/room/${code}`)
            .then(res => this.setState({name: res.data.room.name}))
    }

    render = () => (
        <ContentBoxFluid padding={2}>
            <Stack direction={'row'} justifyContent={'space-between'} minWidth={'min-content'}>
                <IconButton color='primary' size={'small'} href={'/rooms'}>
                    <ArrowBackRounded/>
                </IconButton>
                <Typography variant={'h6'} display={'block'}>{this.state.name}</Typography>
                <Box/>
            </Stack>
        </ContentBoxFluid>
    )
}