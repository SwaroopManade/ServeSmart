import React from 'react';
import { Button, Card } from 'react-bootstrap'; // Import Bootstrap components

function DishItem({ dish, handleAddDish }) {
  return (
    <Card className="mb-3 shadow-sm">
      <Card.Body>
        <Card.Title>{dish.name}</Card.Title>
        <Card.Text>
          Price: <strong>${dish.price}</strong>
        </Card.Text>
        <Button 
          variant="primary" 
          onClick={() => handleAddDish(dish)}
          className="w-100"
        >
          Add
        </Button>
      </Card.Body>
    </Card>
  );
}

export default DishItem;
