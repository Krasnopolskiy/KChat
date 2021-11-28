import * as React from 'react'
import ReactDOM from 'react-dom'
import {Grid, Stack} from '@mui/material'
import {AppContainer} from '../../components/AppContainer'
import {MessageContainer} from './components/MessageContainer'
import {MessageInput} from './components/MessageInput'
import {Header} from './components/Header'

class RoomPage extends React.Component {
    constructor(props) {
        super(props)
    }

    render = () => (
        <AppContainer>
            <Grid container spacing={2} columns={{xs: 4, sm: 8, md: 12}}>
                <Grid item xs={12} sm={12} md={4} justifyContent={'center'} paddingX={4}>
                    <Stack gap={2}>
                        <Header/>
                    </Stack>
                </Grid>
                <Grid item xs={12} sm={12} md={8} paddingX={4}>
                    <Stack gap={2} height={'90vh'}>
                        <MessageContainer/>
                        <MessageInput/>
                    </Stack>
                </Grid>
            </Grid>
        </AppContainer>
    )
}

ReactDOM.render(<RoomPage/>, document.querySelector('#app'));