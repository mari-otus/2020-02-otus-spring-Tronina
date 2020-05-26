import React, { Component } from 'react';

import { Switch, Route, Redirect, withRouter } from 'react-router-dom';

import Home from './components/Home/Home';
import Books from './components/Books/Books';
import Book from './components/Book/Book';
import BookComments from './components/BookComments/BookComments';

import './App.scss';

class App extends Component {
  render() {
    const { history } = this.props;

    return (
      <div className="App">
        <Switch>
          <Route
            history={history}
            path="/library/books/:id"
            component={Book}
          />
          <Route
            history={history}
            path="/library/comments/books/:id"
            component={BookComments}
          />
          <Route history={history} path='/library/home' component={Home} />
          <Route history={history} path='/library/books' component={Books} />
          <Redirect from='/' to='/library/home'/>
        </Switch>
      </div>
    );
  }
}

export default withRouter(App);
