import * as React from 'react';
import {Box, IconButton, TextField} from '@mui/material';
import {SendRounded} from '@mui/icons-material';
import {ContentBoxFluid} from '../../../components/ContentBoxes';

export class MessageInput extends React.Component {
    constructor(props) {
        super(props)
        this.code = window.location.pathname.split('/').at(-1)
        this.state = {value: null, ws: null}
    }

    componentDidMount = () => this.connect()

    connect = () => {
        let ws = new WebSocket(`ws://${window.location.host}${window.location.pathname}/chat`)
        ws.onopen = () => this.setState({ws: ws})
    }

    sendMessage = () => {
        this.state.ws.send(this.state.value)
        this.setState({value: ''})
    }

    handleKeyPress = (event) => {
        if (event.key === 'Enter') this.sendMessage()
    }

    handleChange = event => this.setState({value: event.target.value})

    SendButton = () => <IconButton color={'primary'} onClick={this.sendMessage}><SendRounded/></IconButton>

    render = () => (
        <ContentBoxFluid padding={2}>
            <Box paddingX={2}>
                <TextField value={this.state.value} fullWidth={true} color={'primary'} id='outlined-basic'
                           label='Message' variant='standard' onChange={this.handleChange}
                           onKeyPress={this.handleKeyPress} InputProps={{endAdornment: <this.SendButton/>}}/>
            </Box>
        </ContentBoxFluid>
    )
}