import React, { Component } from 'react';

import './Title.scss';

export default class Title extends Component {

  render() {
    return (
      <h3 className="Title">{this.props.value}</h3>
    )
  }

}
