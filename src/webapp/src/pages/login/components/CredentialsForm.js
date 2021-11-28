import * as React from 'react'
import {Button, Stack, TextField} from '@mui/material'

export const CredentialsForm = () =>
    <form action={'/login'} method={'post'}>
        <Stack gap={3} padding={2}>
            <TextField name='name' label='Name' variant='filled'/>
            <TextField name='password' label='Password' type={'password'} variant='filled'/>
            <Stack direction={'row'} gap={2}>
                <Button variant={'contained'} type={'submit'}>Login</Button>
                <Button variant={'outlined'} href={'/register'}>Register</Button>
            </Stack>
        </Stack>
    </form>