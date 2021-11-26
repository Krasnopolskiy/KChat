import * as React from 'react';

import {Dialog} from "@mui/material";

export class AlertDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {open: false}
    }

    handleClickOpen = () => {
        this.setState({open: true})
    }

    handleClose = () => {
        this.setState({open: false})
    }

    render = () => (
        <Dialog
            open={this.state.open}
            onClose={this.handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description">
            {this.props.children}
        </Dialog>
    )
}