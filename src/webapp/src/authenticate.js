import * as React from 'react';
import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import {CssBaseline, Grid, Stack, TextField, Typography} from "@mui/material";
import {ContentBoxFluid} from "./components/ContentBoxes";

class CredentialsForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '', password: ''
        };

        this.handleNameChange = this.handleNameChange.bind(this)
        this.handlePasswordChange = this.handlePasswordChange.bind(this)
        this.handleSubmitLogin = this.handleSubmitLogin.bind(this)
        this.handleSubmitRegister = this.handleSubmitRegister.bind(this)
    }

    handleNameChange(event) {
        this.setState({name: event.target.value});
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }

    handleSubmitLogin(event) {
        alert(`Login User(${this.state.name}; ${this.state.password})`);
        event.preventDefault();
    }

    handleSubmitRegister(event) {
        alert(`Register User(${this.state.name}; ${this.state.password})`);
        event.preventDefault();
    }

    render() {
        return <Stack gap={3} padding={2}>
            <TextField id="name" label="Name" variant="filled" value={this.state.name}
                       onChange={this.handleNameChange}/>
            <TextField id="password" type={"password"} label="Password" variant="filled" value={this.state.password}
                       onChange={this.handlePasswordChange}/>
            <Stack direction={"row"} gap={2}>
                <Button variant={"contained"} href={"#"} onClick={this.handleSubmitLogin}>Login</Button>
                <Button variant={"outlined"} href={"#"} onClick={this.handleSubmitRegister}>Register</Button>
            </Stack>
        </Stack>
    }
}

const AuthenticatePage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent={"center"} padding={4}>
            <Grid item xs={6}>
                <ContentBoxFluid>
                    <Typography variant={"h2"} textAlign={"center"} gutterBottom>
                        Authenticate
                    </Typography>
                    <CredentialsForm/>
                </ContentBoxFluid>
            </Grid>
        </Grid>
    </React.Fragment>

ReactDOM.render(<AuthenticatePage/>, document.querySelector('#app'))