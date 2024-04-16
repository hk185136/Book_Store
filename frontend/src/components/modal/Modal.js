import React from 'react'
import ReactDom from 'react-dom'
import './Modal.css'

function Modal(props) {
  return ReactDom.createPortal(
    <div className='modal-container'>
        <div className='modal'>
            <img src="/close-icon.jpg" alt="" className='close-button' onClick={()=>props.setIsOpen(false)}/>
            {props.children}   
        </div> 
    </div>,
    document.getElementById('portal')
  )
}

export default Modal