import { useEffect } from 'react';
import { hotjar } from 'react-hotjar';

const Hotjar = () => {
    // Your Hotjar configuration

    const hjid = process.env.REACT_APP_HOTJAR_ID;
    const hjsv = 6;

    useEffect(() => {
        // Initialize Hotjar
        hotjar.initialize(hjid, hjsv);

        // Identify the user
        hotjar.identify(hjid, { userProperty: 'value' });

        // Add an event
        hotjar.event('button-click');

        // Update SPA state
        hotjar.stateChange('/school');
        hotjar.stateChange('/desk');
        hotjar.stateChange('/schoolLetterWritePage');
    }, []); // Empty dependency array ensures this effect runs once after the component mounts

    return null; // Hotjar.js doesn't render anything, so return null
};

export default Hotjar;
