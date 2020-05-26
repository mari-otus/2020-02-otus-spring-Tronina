import React, { Component } from 'react';
import Header from '../Header/Header';

export default class Error extends Component {

  render () {
    return (
      <div>
        <Header/>
        Ошибка: {this.props.message}
      </div>
    )
  }
}
