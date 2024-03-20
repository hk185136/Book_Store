import React from 'react'
import './Sidebar.css';
import Rating from '../rating/Rating';
function Sidebar() {
  return (
    <div className='sidebar'>
        <h2 className='heading-filter'>Genre</h2>
        <div className='grid-filter'>
            <label htmlFor="horror">horror</label>
            <input type="checkbox" id='horror' name='horror' className='checkbox'/>
            <label htmlFor="">romance</label>
            <input type="checkbox" className='checkbox'/>
            <label htmlFor="">thriller</label>
            <input type="checkbox" className='checkbox'/>
            <label htmlFor="">fantasy</label>
            <input type="checkbox" className='checkbox'/>
            <label htmlFor="">historical</label>
            <input type="checkbox" className='checkbox'/>
            <label htmlFor="">mystery</label>
            <input type="checkbox" className='checkbox'/>
        </div>

        <h2 className='heading-filter'>Price</h2>
        <div className='grid-filter'>
            <label htmlFor="">Under &#8377;1000</label>
            <input type="checkbox" className='checkbox'/>
            <label htmlFor="">&#8377;1000 - &#8377;5000</label>
            <input type="checkbox" className='checkbox'/>
            <label htmlFor="">&#8377;5000 - &#8377;20,000</label>
            <input type="checkbox" className='checkbox'/>
        </div>

        <h2 className='heading-filter'>Min. Rating</h2>
        <div>
            <Rating count ={1}/>
            <Rating count ={2}/>
            <Rating count ={3}/>
            <Rating count ={4}/>
            <Rating count ={5}/>
        </div>
       
    </div>
  )
}

export default Sidebar