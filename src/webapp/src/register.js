import * as React from 'react';
import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import {CssBaseline, Grid, Stack, TextField, Typography} from "@mui/material";
import {ContentBoxFluid} from "./components/ContentBoxes";

const CredentialsForm = () =>
    <form action={"/api/register"} method={"post"}>
        <Stack gap={3} padding={2}>
            <TextField name="name" label="Name" variant="filled"/>
            <TextField name="password" label="Password" type={"password"} variant="filled"/>
            <Stack direction={"row"} gap={2}>
                <Button variant={"outlined"} href={"/login"}>Login</Button>
                <Button variant={"contained"} type={"submit"}>Register</Button>
            </Stack>
        </Stack>
    </form>

const RegisterPage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent={"center"} padding={4}>
            <Grid item xs={6}>
                <ContentBoxFluid>
                    <Typography variant={"h2"} textAlign={"center"} gutterBottom>Register</Typography>
                    <CredentialsForm/>
                </ContentBoxFluid>
            </Grid>
        </Grid>
    </React.Fragment>

ReactDOM.render(<RegisterPage/>, document.querySelector('#app'))