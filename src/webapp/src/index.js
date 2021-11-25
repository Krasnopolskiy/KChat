import * as React from 'react';
import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';

function IndexPage() {
    return <Button variant="contained">Hello World</Button>;
}

ReactDOM.render(<IndexPage/>, document.querySelector('#app'));