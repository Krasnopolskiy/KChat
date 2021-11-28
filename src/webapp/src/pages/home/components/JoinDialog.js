import * as React from 'react'
import {Button, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField} from '@mui/material'
import {AlertDialog} from './AlertDialog'

export class JoinDialog extends React.Component {
    constructor(props) {
        super(props)
        this.state = {open: false}
        this.dialog = React.createRef()
    }

    handleClose = () => this.dialog.current.handleClose()

    handleOpen = () => this.dialog.current.handleOpen()

    render = () => (
        <AlertDialog ref={this.dialog} onClose={this.handleClose}>
            <DialogTitle id='alert-dialog-title'>
                Join existing room
            </DialogTitle>
            <form method='get' action={'/api/room/enter'}>
                <DialogContent>
                    <DialogContentText>
                        To join a room, enter a six-digit code
                    </DialogContentText>
                    <TextField margin={'dense'} fullWidth={true} autoFocus name={'code'} label='Invite code'
                               variant='outlined'/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose}>Cancel</Button>
                    <Button type={'submit'} variant={'contained'}>Join</Button>
                </DialogActions>
            </form>
        </AlertDialog>
    )
}