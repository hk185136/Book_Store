import React from 'react'
import Nav from '../../components/nav/Nav';
import Sidebar from '../../components/sidebar/Sidebar';
import Books from '../../components/booklist/Books';
import {BrowserRouter as Router, Route ,Routes, Outlet} from 'react-router-dom';
import Cart from '../cart/Cart';
function Home({books,setBooks}) {
  return (
    <div>
      <Nav books = {books} setBooks = {setBooks}/>
      <Outlet/>
    </div>
  )
}

export default Home