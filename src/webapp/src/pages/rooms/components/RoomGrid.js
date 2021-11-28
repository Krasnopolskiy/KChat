import * as React from 'react';
import axios from 'axios';
import {Grid} from '@mui/material';
import {RoomCard} from './RoomCard';

export class RoomGrid extends React.Component {
    constructor(props) {
        super(props)
        this.state = {rooms: []}
    }

    componentDidMount = () =>
        axios.get('/api/user', {withCredentials: true})
            .then(res => this.setState({rooms: res.data.user.rooms}))

    render = () => (
        <Grid container justifyContent={'center'}>
            {this.state.rooms.map(room =>
                <Grid item>
                    <RoomCard sx={{width: 'max-content'}} name={room.name} code={room.code}/>
                </Grid>
            )}
        </Grid>
    )
}