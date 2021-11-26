import * as React from 'react';
import ReactDOM from 'react-dom';
import {ContentBoxFluid} from "./components/ContentBoxes";
import {AppContainer} from "./components/AppContainer";
import {IconButton, Stack, TextField, Typography} from "@mui/material";
import {ArrowBackRounded, SendRounded, SettingsRounded} from "@mui/icons-material";


class RoomPage extends React.Component {
    constructor(props) {
        super(props)
    }

    Header = () => (
        <Stack direction={"row"} gap={2} justifyContent={"space-between"}>
            <IconButton color="primary" size={"small"}>
                <ArrowBackRounded/>
            </IconButton>
            <Typography variant={"h5"} display={"block"}>Room name</Typography>
            <IconButton disabled color="primary" size={"small"}>
                <SettingsRounded/>
            </IconButton>
        </Stack>
    )

    MessageInput = () => (
        <TextField fullWidth={true} id="outlined-basic" label="Message" variant="outlined"
                   InputProps={{endAdornment: <IconButton children={<SendRounded/>}/>}}/>
    )

    render = () => (
        <AppContainer>
            <Stack gap={2}>
                <ContentBoxFluid padding={1}>
                    <Stack container height={"70vh"}>
                        <ContentBoxFluid padding={2}>
                            <this.Header/>
                        </ContentBoxFluid>
                    </Stack>
                </ContentBoxFluid>
                <ContentBoxFluid>
                    <this.MessageInput/>
                </ContentBoxFluid>
            </Stack>
        </AppContainer>
    )
}


ReactDOM.render(<RoomPage/>, document.querySelector('#app'));