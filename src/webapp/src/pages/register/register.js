import * as React from 'react';
import ReactDOM from 'react-dom';
import {CssBaseline, Grid, Typography} from '@mui/material';
import {ContentBoxFluid} from '../../components/ContentBoxes';
import {CredentialsForm} from './components/CredentialsForm';

const RegisterPage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent={'center'} padding={4}>
            <Grid item xs={6}>
                <ContentBoxFluid>
                    <Typography variant={'h2'} textAlign={'center'} gutterBottom>Register</Typography>
                    <CredentialsForm/>
                </ContentBoxFluid>
            </Grid>
        </Grid>
    </React.Fragment>

ReactDOM.render(<RegisterPage/>, document.querySelector('#app'))