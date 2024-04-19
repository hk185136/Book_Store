import React from 'react'

function HIstoryItem({history,handleDelete}) {
    const sentence = {
        Cancelled : 'Cancelled the order of '+history.item.quantity+' units of '+history.item.book.title+' book',
        Confirmed : 'Order confirmed : '+history.item.quantity+' units of '+history.item.book.title+' book',
        Delivered : 'Order delivered : \n'+history.item.quantity+' units of '+history.item.book.title+'\nbook Address : '+history.item.user.address,
        'On the way' : 'Order is on the way : \n'+history.item.quantity+' units of '+history.item.book.title+'\nbook Address : '+history.item.user.address
    }
  return (
    <tr>
        <td style={{color : 'green'}}>{history.date}</td>
        <td>{sentence[history.item.status]}</td>
        <td>
          <img 
            style={{cursor:'pointer'}} 
            src='/delete-button.png' 
            height={20} 
            onClick={()=>handleDelete(history.id)}
          >
          </img>
        </td>
    </tr>
  )
}

export default HIstoryItem