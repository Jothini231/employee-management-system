import React from 'react';
import ProfileSettings from '../../components/settings/ProfileSettings';

const Settings = () => {
    return (
        <div className="max-w-6xl mx-auto py-6">
            <div className="mb-8">
                <h1 className="text-3xl font-bold text-gray-800">Settings</h1>
                <p className="text-gray-500 mt-1">Manage system settings and personal profile.</p>
            </div>
            
            <ProfileSettings />
        </div>
    );
};

export default Settings;
