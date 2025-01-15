import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import instance from '../services/api';

const CategoryForm = () => {
    const [formData, setFormData] = useState({ label: '' });
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
        ...formData,
        [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
        await instance.post('/categories/save', formData);
        alert('Category created successfully');
        navigate('/categories'); // Navigate to the category list page
        } catch (error) {
        console.error('Error creating category:', error);
        alert('Failed to create category');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
        <div>
            <label className="block text-sm font-medium">Category Label</label>
            <input
            type="text"
            name="label"
            value={formData.label}
            onChange={handleChange}
            className="w-full border rounded px-2 py-1"
            placeholder="Enter category name"
            required
            />
        </div>
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
            Create Category
        </button>
        </form>
    );
};

export default CategoryForm;
