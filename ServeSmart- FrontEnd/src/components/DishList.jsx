import React from 'react';
import DishItem from './DishItem';

function DishList({ dishes, handleAddDish }) {
  return (
    <div className="row">
      {dishes.map((dish) => (
        <div className="col-md-4" key={dish.id}>
          <DishItem dish={dish} handleAddDish={handleAddDish} />
        </div>
      ))}
    </div>
  );
}

export default DishList;
