import React, { Component } from 'react';

import './Header.scss';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import { ReactComponent as Library } from '../../images/library.svg'
import Typography from '@material-ui/core/Typography';

export default class Header extends Component {

  render () {

    return (
      <div className="Header">
          <AppBar position="static">
            <Toolbar>
              <Typography variant="h7">
                <Library className='Header-Icon' />
                Библиотека
              </Typography>
              <Button color="inherit" href="/library/home">Главная</Button>
              <Button color="inherit" href="/library/books">Книги</Button>
              <div className="Test"></div>
              <Button color="inherit" className="Book-Button"  href="/library/books/new">
                Добавить книгу
              </Button>
            </Toolbar>
          </AppBar>
      </div>
    )
  }
}
