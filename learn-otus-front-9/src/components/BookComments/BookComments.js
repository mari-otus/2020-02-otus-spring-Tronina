import React, { Component } from 'react';
import Header from '../Header/Header';

import './BookComments.scss';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Loader from '../Loader/Loader';
import Table from '../Table/Table';
import Error from '../Error/Error';
import Title from '../Title/Title';

export default class BookComments extends Component {

  state = {
    data: null,
    book: null,
    isLoading: false
  };

  componentDidMount() {
    const {
      match: {
        params: { id: userId }
      }
    } = this.props;
    this.fetchBook(userId);
  }

  fetchBook(userId) {
    fetch(`/library/api/books/${userId}`, { method: 'GET' })
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoading: false,
            book: result
          });
          this.fetchComments(userId);
        },
        (error) => {
          this.setState({
            isLoading: false,
            error
          });
        }
      )
  };

  fetchComments(userId) {
    fetch(`/library/api/books/${userId}/comments`, { method: 'GET' })
        .then(
          res => {
            if (res.status === 200) {
              return res.json();
            } else {
              return [];
            }
          }
        )
        .then(
          (result) => {
            this.setState({
              isLoading: false,
              data: result
            });
          },
          (error) => {
            this.setState({
              isLoading: false,
              error
            });
          }
        )
  };

  handleCancelClick = () => {
    const { history } = this.props;
    history.push(`/library/books`);
  };

  render() {
    const {
      error,
      book,
      data,
      isLoading
    } = this.state;

    if (error) {
      return (
        <div>
          <Error message={error.message}/>
        </div>
      )
    }

    const title = `Комментарии к книге "${book?.name}"`;

    return (
      <div>
        <Header/>
        <Title value={title} />
        <Container maxWidth="md">
          <div className='BookComments-Body'>
            {isLoading ? (<Loader/>) : data ? (
              <Table
                data={data}
                columns={[
                  {
                    dataField: 'comment',
                    text: 'Комментарии',
                  },
                ]}
              />) : 'Нет данных'}
          </div>
          <Button variant="contained" color="primary" className="BookComments-Button" onClick={this.handleCancelClick}>
            Выйти
          </Button>
        </Container>
      </div>
    )
  }
}

