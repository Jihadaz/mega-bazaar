import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import instance from '../services/api';
import { toast } from 'react-toastify';

const OrderForm = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const preFilledProduct = location.state?.product;

  const [formData, setFormData] = useState({
    email: '',
    cin: '',
    city: '',
    address: '',
    country: '',
    orderedBy: '',
    receivedBy: '',
  });
  const [productQuantity, setProductQuantity] = useState({});
  const [products, setProducts] = useState([]);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (!preFilledProduct) {
      fetchProducts();
    } else {
      setProducts([preFilledProduct]);
      setProductQuantity({ [preFilledProduct.id]: 1 });
    }
  }, [preFilledProduct]);

  const fetchProducts = async () => {
    try {
      const response = await instance.get('/products');
      setProducts(response.data.products);
    } catch (error) {
      console.error('Error fetching products:', error);
      toast.error("Error fetching products. Please try again later.");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormErrors({ ...formErrors, [name]: null });
    setFormData({ ...formData, [name]: value });
  };

  const handleProductQuantityChange = (id, value) => {
    const quantity = parseInt(value, 10);
    if (quantity < 0) {
      toast.error('Quantity cannot be negative.');
      return;
    }
    setProductQuantity((prevQuantities) => ({
      ...prevQuantities,
      [id]: quantity || 0,
    }));
  };

  const validateForm = () => {
    const errors = {};
    const requiredFields = ['email', 'cin', 'city', 'address', 'country', 'orderedBy', 'receivedBy'];
    requiredFields.forEach((field) => {
      if (!formData[field]) {
        errors[field] = `${field} is required`;
      }
    });
    if (formData.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      errors.email = 'Invalid email format';
    }

    const hasValidQuantity = Object.values(productQuantity).some((qty) => qty > 0);
    if (!hasValidQuantity) {
      errors.quantity = 'Select at least one product quantity.';
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setIsSubmitting(true);
    try {
      const orderData = {
        ...formData,
        orderedProductDtos: Object.entries(productQuantity)
          .filter(([, qty]) => qty > 0)
          .map(([productId, qty]) => {
            const product = products.find((p) => p.id === parseInt(productId, 10));
            return {
              productId: parseInt(productId, 10),
              quantity: qty,
              price: product?.price || 0,
            };
          }),
      };

      await instance.post('/orders/save', orderData);
      toast.success("Order submitted successfully!");
      navigate('/order-sumbitted');
    } catch (error) {
      console.error('Error creating order:', error);
      toast.error("Failed to submit order. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mx-auto p-8 bg-white rounded-lg shadow-md mt-10">
      <h1 className="text-3xl font-bold mb-6 text-gray-800">Create Order</h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        {Object.keys(formData).map((key) => (
          <div key={key}>
            <label htmlFor={key} className="block text-sm font-medium text-gray-700 mb-1 capitalize">
              {key.replace(/([A-Z])/g, ' $1')}
            </label>
            <input
              type={key === "email" ? "email" : "text"}
              id={key}
              name={key}
              value={formData[key]}
              onChange={handleChange}
              className={`w-full rounded-md ring-1 ring-lime-300 border-gray-300 shadow-sm focus:border-lime-500 focus:ring focus:ring-lime-200 sm:text-sm px-3 py-2 ${
                formErrors[key] ? 'border-red-500' : ''
              }`}
              placeholder={`Enter your ${key.replace(/([A-Z])/g, ' $1').toLowerCase()}`}
            />
            {formErrors[key] && <p className="text-red-500 text-sm mt-1">{formErrors[key]}</p>}
          </div>
        ))}

        <div>
          <h2 className="text-lg font-medium text-gray-700 mb-3">Products</h2>
          {products.map((product) => (
            <div key={product.id} className="flex items-center justify-between border-b py-3">
              <div>
                <p className="font-medium text-gray-800">{product.label}</p>
                <p className="text-sm text-gray-600">${product.price?.toFixed(2) || 0}</p>
              </div>
              <input
                type="number"
                min="0"
                value={productQuantity[product.id] || 0}
                onChange={(e) => handleProductQuantityChange(product.id, e.target.value)}
                className="border rounded ring-1 ring-lime-300 px-2 py-1 w-20 text-gray-700 focus:ring focus:ring-lime-200"
              />
            </div>
          ))}
          {formErrors.quantity && <p className="text-red-500 text-sm mt-1">{formErrors.quantity}</p>}
        </div>

        <button
          type="submit"
          disabled={isSubmitting}
          className="w-full bg-lime-600 hover:bg-lime-700 text-white font-bold py-3 px-6 rounded transition duration-300 focus:outline-none focus:ring-2 focus:ring-lime-500 focus:ring-opacity-50 disabled:bg-gray-400 disabled:cursor-not-allowed"
        >
          {isSubmitting ? (
            <span className="animate-spin mr-2 h-5 w-5 border-t-2 border-b-2 border-white rounded-full inline-block"></span>
          ) : null}
          {isSubmitting ? 'Submitting...' : 'Submit Order'}
        </button>
      </form>
    </div>
  );
};

export default OrderForm;
