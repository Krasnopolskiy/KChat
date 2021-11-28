import * as React from 'react'
import ReactDOM from 'react-dom'
import {Button, Stack, Typography} from '@mui/material'
import {ContentBoxFluid, ContentBoxWrap} from '../../components/ContentBoxes'
import {AppContainer} from '../../components/AppContainer'
import {JoinDialog} from './components/JoinDialog'
import {CreateDialog} from './components/CreateDialog'


class HomePage extends React.Component {
    constructor(props) {
        super(props)
        this.joinDialog = React.createRef()
        this.createDialog = React.createRef()
    }

    handleJoinDialogOpen = () => this.joinDialog.current.handleOpen()

    handleCreateDialogOpen = () => this.createDialog.current.handleOpen()

    render = () => (
        <React.Fragment>
            <JoinDialog ref={this.joinDialog}/>
            <CreateDialog ref={this.createDialog}/>
            <AppContainer>
                <Stack gap={4}>
                    <ContentBoxFluid padding={4}>
                        <Typography variant={'h2'} textAlign={'center'}>Welcome to KChat!</Typography>
                    </ContentBoxFluid>
                    <ContentBoxWrap>
                        <Stack direction={'row'} gap={2}>
                            <Button variant={'contained'} size={'large'}
                                    onClick={this.handleJoinDialogOpen}>Join room</Button>
                            <Button variant={'contained'} size={'large'}
                                    onClick={this.handleCreateDialogOpen}>Create room</Button>
                        </Stack>
                    </ContentBoxWrap>
                </Stack>
            </AppContainer>
        </React.Fragment>
    )
}


ReactDOM.render(<HomePage/>, document.querySelector('#app'));