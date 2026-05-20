import React, { useState, useRef, useEffect } from 'react';
import { getEmployeeById } from '../../services/employeeService';
import { updateProfile, changePassword } from '../../services/settingsService';
import { FaCamera } from 'react-icons/fa';

const ProfileSettings = () => {
    
    const employeeId = 1;
    const [profileData, setProfileData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        contactNumber: '',
        designation: '',
        photo: null
    });
    
    const [passwordData, setPasswordData] = useState({
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
    });

    const [previewImage, setPreviewImage] = useState(null);
    const fileInputRef = useRef(null);

    
    useEffect(() => {
        
        const fetchProfile = async () => {
            try {
                
                const response = await getEmployeeById(employeeId);
                if (response.data && response.data.data) {
                    const emp = response.data.data;
                    setProfileData({
                        firstName: emp.firstName || '',
                        lastName: emp.lastName || '',
                        email: emp.email || '',
                        contactNumber: emp.contactNumber || '',
                        designation: emp.designation || '',
                        photo: emp.photo || null
                    });
                    if (emp.photo) {
                        setPreviewImage(`data:image/jpeg;base64,${emp.photo}`);
                    }
                }
            } catch (error) {
                console.error("Error fetching profile", error);
            }
        };
        fetchProfile();
    }, []);

    const handleProfileChange = (e) => {
        const { name, value } = e.target;
        setProfileData(prev => ({ ...prev, [name]: value }));
    };

    const handlePasswordChange = (e) => {
        const { name, value } = e.target;
        setPasswordData(prev => ({ ...prev, [name]: value }));
    };

    const handleImageClick = () => {
        fileInputRef.current.click();
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                const base64String = reader.result;
                setPreviewImage(base64String);
                const base64Data = base64String.split(',')[1];
                setProfileData(prev => ({ ...prev, photo: base64Data }));
            };
            reader.readAsDataURL(file);
        }
    };

    const saveProfile = async () => {
        try {
            const response = await updateProfile(employeeId, profileData);
            if (response.status === 200) {
                alert("Profile saved successfully!");
            }
        } catch (error) {
            console.error("Error saving profile", error);
            alert("Failed to save profile.");
        }
    };

    const savePassword = async () => {
        if (passwordData.newPassword !== passwordData.confirmPassword) {
            alert("New passwords do not match!");
            return;
        }
        try {
            const response = await changePassword(employeeId, passwordData);
            if (response.status === 200) {
                alert("Password changed successfully!");
                setPasswordData({ currentPassword: '', newPassword: '', confirmPassword: '' });
            }
        } catch (error) {
            console.error("Error changing password", error);
            alert("Failed to change password. Please check your current password.");
        }
    };

    const resetProfile = async () => {
        try {
            const response = await getEmployeeById(employeeId);
            if (response.data && response.data.data) {
                const emp = response.data.data;
                setProfileData({
                    firstName: emp.firstName || '',
                    lastName: emp.lastName || '',
                    email: emp.email || '',
                    contactNumber: emp.contactNumber || '',
                    designation: emp.designation || '',
                    photo: emp.photo || null
                });
                if (emp.photo) {
                    setPreviewImage(`data:image/jpeg;base64,${emp.photo}`);
                } else {
                    setPreviewImage(null);
                }
            }
        } catch (error) {
            console.error("Error resetting profile", error);
        }
    };

    const resetPassword = () => {
        setPasswordData({ currentPassword: '', newPassword: '', confirmPassword: '' });
    };

    return (
        <div className="flex flex-col gap-6">
           
            <div className="bg-white rounded border border-gray-200 shadow-sm p-8">
                <h3 className="text-xl font-semibold mb-6 text-gray-800">My Profile</h3>
                
                <div className="flex flex-col sm:flex-row gap-8">
                    
                    <div className="flex flex-col items-center">
                        <div 
                            className="relative w-32 h-32 rounded-full overflow-hidden border border-gray-300 cursor-pointer group bg-gray-100 flex justify-center items-center"
                            onClick={handleImageClick}
                        >
                            {previewImage ? (
                                <img src={previewImage} alt="Profile" className="w-full h-full object-cover" />
                            ) : (
                                <span className="text-gray-400 text-4xl">{profileData.firstName.charAt(0) || 'A'}</span>
                            )}
                            
                            
                            <div className="absolute inset-0 bg-black bg-opacity-40 flex justify-center items-center opacity-0 group-hover:opacity-100 transition-opacity duration-200">
                                <FaCamera className="text-white text-2xl" />
                            </div>
                        </div>
                        <input 
                            type="file" 
                            ref={fileInputRef} 
                            onChange={handleImageChange} 
                            accept="image/*" 
                            className="hidden" 
                        />
                        <p className="text-xs text-gray-500 mt-2 text-center">Click image to change</p>
                    </div>

                    
                    <div className="flex-1 grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">First Name</label>
                            <input 
                                type="text" 
                                name="firstName" 
                                value={profileData.firstName} 
                                onChange={handleProfileChange}
                                className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Last Name</label>
                            <input 
                                type="text" 
                                name="lastName" 
                                value={profileData.lastName} 
                                onChange={handleProfileChange}
                                className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                            />
                        </div>
                        <div className="md:col-span-2">
                            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                            <input 
                                type="email" 
                                name="email" 
                                value={profileData.email} 
                                onChange={handleProfileChange}
                                className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Phone Number</label>
                            <input 
                                type="text" 
                                name="contactNumber" 
                                value={profileData.contactNumber} 
                                onChange={handleProfileChange}
                                className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Designation</label>
                            <input 
                                type="text" 
                                name="designation" 
                                value={profileData.designation} 
                                onChange={handleProfileChange}
                                className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                            />
                        </div>
                    </div>
                </div>
                
                <div className="mt-6 flex justify-end gap-3">
                    <button 
                        onClick={resetProfile}
                        className="px-5 py-2 rounded border border-gray-300 text-gray-700 hover:bg-gray-50 transition duration-150"
                    >
                        Cancel
                    </button>
                    <button 
                        onClick={saveProfile}
                        className="bg-blue-600 text-white px-5 py-2 rounded hover:bg-blue-700 transition duration-150"
                    >
                        Save Profile
                    </button>
                </div>
            </div>

            
            <div className="bg-white rounded border border-gray-200 shadow-sm p-8">
                <h3 className="text-xl font-semibold mb-6 text-gray-800">Change Password</h3>
                
                <div className="space-y-4 max-w-md">
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Current Password</label>
                        <input 
                            type="password" 
                            name="currentPassword"
                            value={passwordData.currentPassword}
                            onChange={handlePasswordChange}
                            placeholder="Enter current password"
                            className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">New Password</label>
                        <input 
                            type="password" 
                            name="newPassword"
                            value={passwordData.newPassword}
                            onChange={handlePasswordChange}
                            placeholder="Enter new password"
                            className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Confirm Password</label>
                        <input 
                            type="password" 
                            name="confirmPassword"
                            value={passwordData.confirmPassword}
                            onChange={handlePasswordChange}
                            placeholder="Confirm new password"
                            className="w-full p-2 border border-gray-300 rounded focus:ring-1 focus:ring-blue-500 focus:outline-none" 
                        />
                    </div>
                </div>
                
                <div className="mt-6 flex justify-start gap-3">
                    <button 
                        onClick={resetPassword}
                        className="px-5 py-2 rounded border border-gray-300 text-gray-700 hover:bg-gray-50 transition duration-150"
                    >
                        Cancel
                    </button>
                    <button 
                        onClick={savePassword}
                        className="bg-green-600 text-white px-5 py-2 rounded hover:bg-green-700 transition duration-150"
                    >
                        Save Password
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ProfileSettings;
