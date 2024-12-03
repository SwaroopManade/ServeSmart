import React, { useEffect, useState, useCallback } from 'react';
import { FaTimes } from 'react-icons/fa';
import { getAllDishes, getOrderByTableId, updateOrder, saveOrder, deleteOrdersByTableId } from '../service/ApiService';
import 'bootstrap/dist/css/bootstrap.min.css';

function OrderModal({ table, closeModal, onCheckout }) {
    const [dishes, setDishes] = useState([]);
    const [orders, setOrders] = useState({ selectedDishes: [], totalAmount: 0 });
    const [loading, setLoading] = useState(true);
    const [orderExists, setOrderExists] = useState(false);
    const [message, setMessage] = useState('');

    const fetchDishes = useCallback(async () => {
        try {
            const response = await getAllDishes();
            setDishes(response.data || []);
        } catch (error) {
            console.error('Error fetching dishes:', error);
        }
    }, []);

    const fetchOrdersByTableId = useCallback(async () => {
        if (!table || !table.id) return;

        try {
            const response = await getOrderByTableId(table.id);
            if (response && response.data && response.data.length > 0) {
                setOrderExists(true);
                const ordersData = response.data;
                const mergedDishes = [];
                let totalAmount = 0;

                ordersData.forEach(order => {
                    order.dishes.forEach(dish => {
                        mergedDishes.push(dish);
                        totalAmount += dish.price;
                    });
                });

                setOrders({ selectedDishes: mergedDishes, totalAmount });
            } else {
                setOrderExists(false);
                setOrders({ selectedDishes: [], totalAmount: 0 });
            }
        } catch (error) {
            console.error('Error fetching orders by table ID:', error);
        }
    }, [table]);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            await fetchDishes();
            await fetchOrdersByTableId();
            setLoading(false);
        };

        fetchData();
    }, [fetchDishes, fetchOrdersByTableId]);

    const addDishToOrder = (dish) => {
        setOrders((prev) => {
            const updatedDishes = [...prev.selectedDishes, dish];
            const newTotalAmount = prev.totalAmount + dish.price;

            return { selectedDishes: updatedDishes, totalAmount: newTotalAmount };
        });
    };

    const removeDishFromOrder = (dishToRemove) => {
        setOrders((prev) => {
            const updatedDishes = prev.selectedDishes.filter(dish => dish.id !== dishToRemove.id);
            const newTotalAmount = updatedDishes.reduce((total, dish) => total + dish.price, 0);

            return { selectedDishes: updatedDishes, totalAmount: newTotalAmount };
        });
    };

    const handleSave = async () => {
        if (!orders || !table || !table.id) {
            console.error('Orders or table data is missing');
            return;
        }

        const orderData = {
            table: {
                id: table.id,
                tableNumber: table.tableNumber,
                status: "Occupied", // Mark the table as occupied
                capacity: table.capacity,
            },
            dishes: orders.selectedDishes || [],
        };

        try {
            if (orderExists) {
                await updateOrder(table.id, orderData);
            } else {
                await saveOrder(orderData);
            }
            setMessage('Order saved successfully!');
            await fetchOrdersByTableId();
        } catch (error) {
            console.error('Error saving order:', error);
        }
    };

    const handleCheckout = async () => {
        setMessage('Checking out...');
        try {
            await deleteOrdersByTableId(table.id);
            closeModal();
            onCheckout(table.id);
        } catch (error) {
            console.error('Error clearing orders:', error);
        }
    };

    if (loading) {
        return (
            <div className="modal fade show" style={{ display: 'block', backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Loading...</h5>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    if (!table) {
        return (
            <div className="modal fade show" style={{ display: 'block', backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">No table selected.</h5>
                            <button type="button" className="close" onClick={closeModal}>&times;</button>
                        </div>
                        <div className="modal-body">
                            <p>Please select a table to place an order.</p>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" onClick={closeModal}>Close</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const currentOrder = orders || { selectedDishes: [], totalAmount: 0 };

    return (
        <div className="modal fade show" style={{ display: 'block', backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
            <div className="modal-dialog modal-lg">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Order for Table {table.tableNumber}</h5>
                        <button type="button" className="close" onClick={closeModal}>&times;</button>
                    </div>
                    <div className="modal-body">
                        {message && <div className="alert alert-info">{message}</div>} {/* Display message */}
                        
                        <div className="row">
                            {dishes.length > 0 ? (
                                dishes.map((dish) => (
                                    <div className="col-md-4 mb-3" key={dish.id}>
                                        <div className="card">
                                            {dish.imageUrl && <img src={dish.imageUrl} className="card-img-top" alt={dish.name} />}
                                            <div className="card-body">
                                                <h6 className="card-title">{dish.name}</h6>
                                                <p className="card-text">Price: ${dish.price}</p>
                                                <button className="btn btn-primary" onClick={() => addDishToOrder(dish)}>Add to Order</button>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p>No dishes available.</p>
                            )}
                        </div>

                        <h5 className="mt-3">Selected Dishes</h5>
                        <ul className="list-group mb-3">
                            {currentOrder.selectedDishes.map((dish, index) => (
                                <li className="list-group-item d-flex justify-content-between align-items-center" key={`${dish.id}-${index}`}>
                                    {dish.name}
                                    <span>${dish.price}</span>
                                    <button className="btn btn-danger btn-sm" onClick={() => removeDishFromOrder(dish)}>
                                        <FaTimes />
                                    </button>
                                </li>
                            ))}
                        </ul>

                        <h5>Total: ${currentOrder.totalAmount.toFixed(2)}</h5>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" onClick={closeModal}>Close</button>
                        <button type="button" className="btn btn-success" onClick={handleSave}>Save</button>
                        <button type="button" className="btn btn-danger" onClick={handleCheckout}>Checkout</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default OrderModal;
