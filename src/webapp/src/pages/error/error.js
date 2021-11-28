import * as React from 'react'
import ReactDOM from 'react-dom'
import Cookies from 'js-cookie'
import {Button, Grid, Stack, Typography} from '@mui/material'
import {ContentBoxWrap} from '../../components/ContentBoxes'

export class ErrorPage extends React.Component{
    constructor(props) {
        super(props)
        this.state = {message: null}
        this.goBack = window.history.back.bind(window.history)
    }

    componentDidMount = () => {
        const cookie = Cookies.get('error') || '%23sNothing there'
        const message = cookie.substring(cookie.indexOf('%23s') + 4).replace('+', ' ')
        this.setState({message: message})
        Cookies.remove('error')
    }

    render = () => (
        <Grid container justifyContent={'center'} padding={4}>
            <ContentBoxWrap>
                <Stack gap={4}>
                    <Typography variant={'h2'} textAlign={'center'}>An error has occurred</Typography>
                    <Typography variant={'h4'} color='error.main'>{this.state.message}</Typography>
                    <Button variant={'contained'} onClick={this.goBack}>Go back</Button>
                </Stack>
            </ContentBoxWrap>
        </Grid>
    )
}

ReactDOM.render(<ErrorPage/>, document.querySelector('#app'));