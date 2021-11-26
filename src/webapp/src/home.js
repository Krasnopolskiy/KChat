import * as React from 'react';
import ReactDOM from 'react-dom';
import {
    Button,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Stack, TextField,
    Typography
} from "@mui/material";
import {ContentBoxFluid, ContentBoxWrap} from "./components/ContentBoxes";
import {AlertDialog} from "./components/AlertDialog";
import {AppContainer} from "./components/AppContainer";


class HomePage extends React.Component {
    constructor(props) {
        super(props)
        this.joinAlertDialog = React.createRef()
        this.createAlertDialog = React.createRef()
        this.handleJoinDialogOpen = this.handleJoinDialogOpen.bind(this)
    }

    handleJoinDialogOpen = () => this.joinAlertDialog.current.handleClickOpen()

    handleJoinDialogClose = () => this.joinAlertDialog.current.handleClose()

    JoinDialog = () => (
        <AlertDialog ref={this.joinAlertDialog}>
            <DialogTitle id="alert-dialog-title">
                Join existing room
            </DialogTitle>
            <form method="get" action={"/api/room/enter"}>
                <DialogContent>
                    <DialogContentText>
                        To join a room, enter a six-digit code
                    </DialogContentText>
                    <TextField margin={"dense"} fullWidth={true} autoFocus name={"code"} label="Invite code"
                               variant="outlined"/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleJoinDialogClose}>Cancel</Button>
                    <Button type={"submit"} variant={"contained"}>Join</Button>
                </DialogActions>
            </form>
        </AlertDialog>
    )

    handleCreateDialogOpen = () => this.createAlertDialog.current.handleClickOpen()

    handleCreateDialogClose = () => this.createAlertDialog.current.handleClose()

    CreateDialog = () => (
        <AlertDialog ref={this.createAlertDialog}>
            <DialogTitle id="alert-dialog-title">
                Create new room
            </DialogTitle>
            <form method="post" action={"/api/room"}>
                <DialogContent>
                    <DialogContentText>
                        To create a room, enter room name
                    </DialogContentText>
                    <TextField margin={"dense"} fullWidth={true} autoFocus name={"name"} label="Room name"
                               variant="outlined"/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleCreateDialogClose}>Cancel</Button>
                    <Button type={"submit"} variant={"contained"}>Create</Button>
                </DialogActions>
            </form>
        </AlertDialog>
    )

    render = () => (
        <React.Fragment>
            <this.JoinDialog/>
            <this.CreateDialog/>
            <AppContainer>
                <Stack gap={4}>
                    <ContentBoxFluid padding={4}>
                        <Typography variant={"h2"} textAlign={"center"}>Welcome to KChat!</Typography>
                    </ContentBoxFluid>
                    <ContentBoxWrap>
                        <Stack direction={"row"} gap={2}>
                            <Button variant={"contained"} size={"large"}
                                    onClick={this.handleJoinDialogOpen}>Join room</Button>
                            <Button variant={"contained"} size={"large"}
                                    onClick={this.handleCreateDialogOpen}>Create room</Button>
                        </Stack>
                    </ContentBoxWrap>
                </Stack>
            </AppContainer>
        </React.Fragment>
    )
}


ReactDOM.render(<HomePage/>, document.querySelector('#app'));