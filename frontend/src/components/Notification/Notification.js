import React from 'react'
import { MdDelete } from "react-icons/md";
function Notification({notification,handleDelete}) {
  return (
    <div className='Notification'>
      <td>{notification.date}</td>
      <td>{notification.message}</td>
      <td><MdDelete onClick={()=>handleDelete(notification.id)} className='message-delete-button'/></td>
    </div>
  )
}

export default Notification