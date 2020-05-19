import React, { Component } from 'react';

import './Books.scss';

import Table from '../Table/Table';
import Header from '../Header/Header';
import Loader from '../Loader/Loader';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import ChatOutlinedIcon from '@material-ui/icons/ChatOutlined';
import Error from '../Error/Error';
import Title from '../Title/Title';

export default class Books extends Component {

  state = {
    data: null,
    isLoading: false,
  }

  componentDidMount() {
    this.fetchBooks()
  }

  fetchBooks() {
    this.setState({ isLoading: true })

    fetch("/library/api/books/")
      .then(res => res.json())
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
  }

  deleteBook(id) {
    if (window.confirm('Are you sure you wish to delete this item?')) {

      this.setState({ isLoading: true })

      fetch("/library/api/books/" + id, {
        method: 'DELETE',
      })
        .then(res => res.json())
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
    }
  }

  render() {
    const { history } = this.props;

    const rowEvents = {
      onDoubleClick: (e, row, rowIndex) => {
        history.push(`/library/books/${row.id}`);
      }
    };

    const {
      error,
      data,
      isLoading
    } = this.state

    if (error) {
      return (
        <div>
          <Error message={error.message}/>
        </div>
      )
    }
    return (
      <div>
        <Header/>
        <Title value={"Список книг"} />
        {isLoading ? (<Loader/>) : data ? (
          <Table
            data={data}
            rowEvents={rowEvents}
            hasHover={true}
            columns={[
              {
                dataField: 'name',
                text: 'Наименование книги',
              },
              {
                dataField: 'yearEdition',
                text: 'Год издания книги',
              },
              {
                dataField: 'authors',
                text: 'Автор',
                sort: true,
                formatter: (v, row) => {
                  return (
                    v.map(author => author.fio)
                      .join(', ')
                  );
                },
              },
              {
                dataField: 'genres',
                text: 'Жанр',
                sort: true,
                formatter: (v, row) => {
                  return (
                    v.map(genre => genre.name)
                      .join(', ')
                  );
                },
              },
              {
                dataField: 'delete',
                isDummyField: true,
                attrs: {
                  title: 'Удалить книгу',
                },
                classes: 'Books-Action',
                events: {
                  onClick: (e, column, columnIndex, row, rowIndex) => {
                    this.deleteBook(row.id);
                  },
                },
                formatter: (v, row) => {
                  return (
                    <DeleteIcon fontSize="small"/>
                  );
                },
              },
              {
                dataField: 'edit',
                isDummyField: true,
                attrs: {
                  title: 'Изменить книгу',
                },
                classes: 'Books-Action',
                events: {
                  onClick: (e, column, columnIndex, row, rowIndex) => {
                    history.push(`/library/books/${row.id}`);
                  },
                },
                formatter: (v, row) => {
                  return (
                    <EditIcon fontSize="small"/>
                  );
                },
              },
              {
                dataField: 'commentView',
                isDummyField: true,
                attrs: {
                  title: 'Просмотреть комментарии',
                },
                classes: 'Books-Action',
                events: {
                  onClick: (e, column, columnIndex, row, rowIndex) => {
                    history.push(`/library/comments/books/${row.id}`);
                  },
                },
                formatter: (v, row) => {
                  return (
                    <ChatOutlinedIcon fontSize="small"/>
                  );
                },
              },
            ]}
          />) : 'Нет данных'}
      </div>
    );
  }
}
