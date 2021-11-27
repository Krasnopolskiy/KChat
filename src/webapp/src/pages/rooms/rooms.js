import * as React from 'react';
import ReactDOM from 'react-dom';
import {Box, Container, Grid, Skeleton, Typography} from "@mui/material";
import {ContentBoxFluid} from "../../components/ContentBoxes";
import {Sidebar} from "../../components/Sidebar";
import {RoomCard} from "./components/RoomCard";
import axios from "axios";


class RoomsPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {loaded: false}
        this.username = null
        axios.get('http://localhost:8080/api/user', {withCredentials: true})
            .then(res => {
                this.username = res.data.user.name
                this.rooms = res.data.rooms
                this.setState({loaded: true})
            })
    }

    roomsGrid = () => (
        <Grid container justifyContent={"center"}>
            {this.rooms.map(room =>
                <Grid item>
                    <RoomCard sx={{width: "max-content"}} name={room.name} code={room.code}/>
                </Grid>
            )}
        </Grid>
    )

    skeleton = () => (
        <Grid container justifyContent={"center"}>
            <Grid item xs={3} padding={2}>
                <Box>
                    <Skeleton/>
                    <Skeleton width="60%"/>
                </Box>
            </Grid>
        </Grid>
    )

    render = () => (
        <Grid container padding={2}>
            <Grid item xs={2}><Sidebar/></Grid>
            <Grid item xs={10}>
                <Container>
                    <ContentBoxFluid>
                        <Typography variant={"h2"} textAlign={"center"} gutterBottom>My rooms</Typography>
                        {this.state.loaded ? <this.roomsGrid/> : <this.skeleton/>}
                    </ContentBoxFluid>
                </Container>
            </Grid>
        </Grid>
    )
}


ReactDOM.render(<RoomsPage/>, document.querySelector('#app'));