import React, { Component } from 'react'

import { map } from 'underscore'

import './Home.scss'

import Header from '../Header/Header'

import { ReactComponent as Library } from '../../images/library.svg'
import { Link } from 'react-router-dom';

const SECTIONS = [
  { title: 'Книги', href: '/library/books', Icon: Library }
]

export default class Home extends Component {

  render () {
    return (
      <div>
        <Header/>
        <div className='Home-Body'>
          <div className='SectionNavigation'>
            {map(SECTIONS, ({ title, href, Icon }) => (
              <Link key={title} className='SectionNavigation-Item Section' to={href}>
                <Icon className='Section-Icon'/>
                <span className='Section-Title'>{title}</span>
              </Link>
            ))}
          </div>
        </div>
      </div>
    )
  }
}
