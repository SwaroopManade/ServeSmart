// Table.js
import React from 'react';
import { Button, Card } from 'react-bootstrap';

function Table({ table, setSelectedTable, handleDeleteTable }) {
  const handleTakeOrder = () => {
    setSelectedTable(table); // Set the selected table in the parent component
  };

  return (
    <Card className="mb-3 shadow-sm">
      <Card.Body>
        <Card.Title>Table {table.tableNumber}</Card.Title>
        <Card.Text>
          Status: <strong>  {table.status}</strong><br />
          Capacity: <strong>{table.capacity}</strong>
        </Card.Text>
        <div className="d-flex justify-content-between">
          <Button variant="success" onClick={handleTakeOrder}>
            Take Order
          </Button>
          <Button 
            variant="danger" 
            onClick={() => handleDeleteTable(table.id)}
          >
            Delete
          </Button>
        </div>
      </Card.Body>
    </Card>
  );
}

export default Table;
