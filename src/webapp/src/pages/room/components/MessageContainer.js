import * as React from 'react'
import {Stack} from '@mui/material'
import {ContentBoxFluid} from '../../../components/ContentBoxes'
import {Message} from './Message'
import axios from "axios";

export class MessageContainer extends React.Component {
    constructor(props) {
        super(props)
        this.code = window.location.pathname.split('/').at(-1)
        this.state = {messages: [], ws: null, user: null}
    }

    componentDidMount = () => {
        axios.get('/api/user', {withCredentials: true})
            .then(res => this.setState({user: res.data.user.name}))
        this.connect()
    }

    connect = () => {
        let ws = new WebSocket(`ws://${window.location.host}${window.location.pathname}/chat`)
        ws.onopen = () => this.setState({ws: ws})
        ws.onmessage = event => {
            this.state.messages.push(JSON.parse(event.data))
            this.setState({messages: this.state.messages})
        }
    }

    render = () => (
        <ContentBoxFluid sx={{maxHeight: 'inherit', height: 'inherit', overflow: 'auto'}}>
            <Stack gap={2}>
                {this.state.messages.map(message =>
                    <Message author={message.user} text={message.text}
                             justifyContent={this.state.name === message.user ? 'right' : 'left'}/>
                )}
            </Stack>
        </ContentBoxFluid>
    )
}

