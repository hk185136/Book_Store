import React from 'react'
import Nav from '../../components/nav/Nav';
import Sidebar from '../../components/sidebar/Sidebar';
import Books from '../../components/booklist/Books';
import {BrowserRouter as Router, Route ,Routes} from 'react-router-dom';
import Cart from '../cart/Cart';
function Home() {
  return (
    <div>
      <Nav/>
      <Sidebar/>
      <Books/>
    </div>
  )
}

export default Home