import React from 'react'
import Book from '../book/Book';
import './Books.css'
function Books() {
  return (
    <div className='books'>
        <Book image='https://cdn.carnegielearning.com/assets/teaser-images/alg1-se-v3.png' name='Mathematics' author='hemanth' price = {1000}/>
    </div>
  )
}

export default Books