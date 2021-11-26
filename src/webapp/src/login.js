import * as React from 'react';
import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import {CssBaseline, Grid, Stack, TextField, Typography} from "@mui/material";
import {ContentBoxFluid} from "./components/ContentBoxes";

const CredentialsForm = () =>
    <form action={"/api/login"} method={"post"}>
        <Stack gap={3} padding={2}>
            <TextField name="name" label="Name" variant="filled"/>
            <TextField name="password" label="Password" type={"password"} variant="filled"/>
            <Stack direction={"row"} gap={2}>
                <Button variant={"contained"} type={"submit"}>Login</Button>
                <Button variant={"outlined"} href={"/register"}>Register</Button>
            </Stack>
        </Stack>
    </form>

const LoginPage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent={"center"} padding={4}>
            <Grid item xs={6}>
                <ContentBoxFluid>
                    <Typography variant={"h2"} textAlign={"center"} gutterBottom>Login</Typography>
                    <CredentialsForm/>
                </ContentBoxFluid>
            </Grid>
        </Grid>
    </React.Fragment>

ReactDOM.render(<LoginPage/>, document.querySelector('#app'))