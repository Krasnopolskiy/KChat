import * as React from 'react'
import ReactDOM from 'react-dom'
import {CssBaseline, Grid, Typography} from '@mui/material'
import {ContentBoxFluid} from '../../components/ContentBoxes'
import {CredentialsForm} from './components/CredentialsForm'

const LoginPage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent={'center'} padding={4}>
            <Grid item xs={6}>
                <ContentBoxFluid>
                    <Typography variant={'h2'} textAlign={'center'} gutterBottom>Login</Typography>
                    <CredentialsForm/>
                </ContentBoxFluid>
            </Grid>
        </Grid>
    </React.Fragment>

ReactDOM.render(<LoginPage/>, document.querySelector('#app'))