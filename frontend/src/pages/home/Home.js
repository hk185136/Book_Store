import React from 'react'
import Nav from '../../components/nav/Nav';
import {Outlet} from 'react-router-dom';

function Home({books,setBooks}) {
  return (
    <div>
      <Nav books = {books} setBooks = {setBooks}/>
      <Outlet/>
    </div>
  )
}

export default Home