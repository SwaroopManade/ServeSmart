import React, { useState } from 'react';
import WelcomePage from './WelcomePage';
import OrderModal from './OrderModal';
import { checkoutOrder } from '../service/ApiService'; // Import the checkout function

function ParentComponent() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedTable, setSelectedTable] = useState(null);
  const [orders, setOrders] = useState({}); // Track orders by table ID

  const openModal = (table) => {
    setSelectedTable(table);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    console.log('Closing modal'); // Debug log
    setIsModalOpen(false);
  };

  const handleCheckout = async () => {
    if (!selectedTable) return; // Ensure selectedTable is available

    const currentOrder = orders[selectedTable.id] || { selectedDishes: [] };

    try {
      const result = await checkoutOrder(selectedTable.id, currentOrder);
      console.log('Order checked out successfully:', result);

      // Reset the orders for the selected table after checkout
      setOrders((prev) => {
        const newOrders = { ...prev };
        delete newOrders[selectedTable.id]; // Remove the order for this table
        return newOrders;
      });

      closeModal(); // Close the modal after checkout
    } catch (error) {
      console.error('Checkout failed:', error);
    }
  };

  return (
    <div>
      <WelcomePage setSelectedTable={setSelectedTable} />
      
      {/* Only render OrderModal if it is open */}
      {isModalOpen && selectedTable && (
        <OrderModal
          table={selectedTable}
          closeModal={closeModal} // Ensure closeModal is passed correctly
          orders={orders} // Pass the current orders to OrderModal
          setOrders={setOrders} // Pass the setOrders function to update orders
          handleCheckout={handleCheckout} // Pass the handleCheckout function
        />
      )}

      {/* Button to open the modal for a specific table */}
      <button onClick={() => openModal(selectedTable)}>Take Order</button>
    </div>
  );
}

export default ParentComponent;
