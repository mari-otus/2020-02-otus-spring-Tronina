import React, { Component } from 'react';
import { TextField } from '@material-ui/core';
import Header from '../Header/Header';

import './Book.scss'
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Loader from '../Loader/Loader';

export default class Book extends Component {

  state = {
    data: null,
    isLoading: false
  }

  componentDidMount() {
    const {
      match: {
        params: { id: userId }
      }
    } = this.props;
    this.fetchBook(userId);
  }

  fetchBook(userId) {
    if (userId !== 'new') {
      fetch(`/library/api/books/${userId}`, { method: 'GET' })
        .then(res => res.json())
        .then(
          (result) => {
            this.setState({
              isLoading: false,
              data: result,
              isNew: false
            });
          },
          (error) => {
            this.setState({
              isLoading: false,
              error
            });
          }
        )
    } else {
      this.setState({
        isLoading: false,
        data: {
          id: null,
          name: '',
          authors: [],
          genres: []
        },
        isNew: true
      });
    }
  };

  handleNameClick = event => {
    const { data } = this.state;
    data.name = event.target.value;
    this.setState({data: data});
  };

  handleYearEditionClick = event => {
    const { data } = this.state;
    if (!event.target.value || event.target.value.length < 5) {
      data.yearEdition = event.target.value;
      this.setState({data: data});
    } else {

    }
  };

  handleAuthorsClick = event => {
    const { data } = this.state;

    const authors = event.target.value.split('\n');
    data.authors = authors.map(newAuthor => {
      const oldAuthor = data.authors.find(author => author.fio.toLowerCase() === newAuthor.toLowerCase());
      if (oldAuthor) {
        return oldAuthor;
      } else {
        return { id: null, fio: newAuthor }
      }
    });

    this.setState({data: data});
  };

  handleGenresClick = event => {
    const { data } = this.state;

    const genres = event.target.value.split('\n');
    data.genres = genres.map(newGenre => {
      const oldGenre = data.genres.find(genre => genre.name.toLowerCase() === newGenre.toLowerCase());
      if (oldGenre) {
        return oldGenre;
      } else {
        return { id: null, name: newGenre }
      }
    });

    this.setState({data: data});
  };

  handleCancelClick = event => {
    const { history } = this.props;
    history.push(`/library/books`);
  };

  handleSaveClick = event => {
    const { history } = this.props;
    this.setState({ isLoading: true });

    const { data } = this.state;

    fetch("/library/api/books/", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoading: false,
            data: result
          });
          history.push(`/library/books`);
        },
        (error) => {
          this.setState({
            isLoading: false,
            error
          });
        }
      ).catch(
        (error) => {
          this.setState({
            isLoading: false,
            error
          });
        });
  };

  render() {
    const {
      error,
      data,
      isLoading,
      isNew
    } = this.state

    if (error) {
      return <div>Error: {error.message}</div>;
    }

    const authors = data?.authors ? data?.authors.map(author => author.fio).join('\n') : "";
    const genres = data?.genres ? data?.genres.map(genre => genre.name).join('\n') : "";

    const title = isNew ? 'Добавление книги' : `Книга "${data?.name}"`;

    return (
      <form noValidate autoComplete="off">
        <Header/>
      <Container maxWidth="sm">
          <div className='Book'>
            <h2>{title}</h2>
            {isLoading ? ( <Loader /> ) : (
            <div className='Book-Body'>
              <TextField
                label='Наименование'
                id='name'
                InputLabelProps={{ shrink: true }}
                value={data?.name}
                multiline
                fullWidth
                margin="normal"
                size="small"
                variant="outlined"
                className='Book-TextField'
                onChange={this.handleNameClick}
              />
              <TextField
                label='Год издания'
                id='yearEdition'
                InputLabelProps={{ shrink: true }}
                value={data?.yearEdition}
                size="small"
                margin="normal"
                variant="outlined"
                className='Book-TextField'
                onChange={this.handleYearEditionClick}
              />
              <TextField
                label='Авторы'
                id='authors'
                InputLabelProps={{ shrink: true }}
                value={authors}
                multiline
                fullWidth
                margin="normal"
                variant="outlined"
                size="small"
                className='Book-TextField'
                helperText="Каждого нового автора вводите с новой строки"
                onChange={this.handleAuthorsClick}
              />
              <TextField
                label='Жанры'
                id='genres'
                InputLabelProps={{ shrink: true }}
                value={genres}
                multiline
                fullWidth
                margin="normal"
                variant="outlined"
                size="small"
                className='Book-TextField'
                helperText="Каждый новый жанр вводите с новой строки"
                onChange={this.handleGenresClick}
              />
            </div>)}
          </div>
          <Button variant="contained" color="primary" className="Book-Button" onClick={this.handleSaveClick}>
            Сохранить
          </Button>
          <Button variant="contained" color="primary" className="Book-Button" onClick={this.handleCancelClick}>
            Выйти
          </Button>
      </Container>
      </form>
    )
  }
}

