import * as React from 'react';
import ReactDOM from 'react-dom';
import {AppContainer} from "../../components/AppContainer";
import {Grid, Stack} from "@mui/material";
import {MessageContainer} from "./components/MessageContainer";
import {MessageInput} from "./components/MessageInput";
import {Header} from "./components/Header";
import axios from "axios";

class RoomPage extends React.Component {
    constructor(props) {
        super(props)
        this.code = window.location.pathname.split('/').at(-1)
    }

    retrieveData = () => {
        axios.get(`http://localhost:8080/api/room/${this.code}`, {withCredentials: true})
            .then(res => console.log(res))
    }

    render = () => (
        <AppContainer pending={this.retrieveData}>
            <Grid container spacing={2} columns={{xs: 4, sm: 8, md: 12}} paddingX={2}>
                <Grid item xs={12} sm={12} md={4} justifyContent={"center"} paddingX={4}>
                    <Stack gap={2}>
                        <Header name={"Room name"}/>
                    </Stack>
                </Grid>
                <Grid item xs={12} sm={12} md={8} paddingX={4}>
                    <Stack gap={2} height={"90vh"}>
                        <MessageContainer/>
                        <MessageInput/>
                    </Stack>
                </Grid>
            </Grid>
        </AppContainer>
    )
}

ReactDOM.render(<RoomPage/>, document.querySelector('#app'));