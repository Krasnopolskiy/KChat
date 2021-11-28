import * as React from 'react'
import {Button, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from '@mui/material'
import {AlertDialog} from './AlertDialog'

export class CreateDialog extends React.Component {
    constructor(props) {
        super(props)
        this.state = {open: false}
        this.dialog = React.createRef()
    }

    handleClose = () => this.dialog.current.handleClose()

    handleOpen = () => this.dialog.current.handleOpen()

    render = () => (
        <AlertDialog ref={this.dialog}>
            <DialogTitle id='alert-dialog-title'>
                Create new room
            </DialogTitle>
            <form method='post' action={'/api/room'}>
                <DialogContent>
                    <DialogContentText>
                        To create a room, enter room name
                    </DialogContentText>
                    <TextField margin={'dense'} fullWidth={true} autoFocus name={'name'} label='RoomCard name'
                               variant='outlined'/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose}>Cancel</Button>
                    <Button type={'submit'} variant={'contained'}>Create</Button>
                </DialogActions>
            </form>
        </AlertDialog>
    )
}