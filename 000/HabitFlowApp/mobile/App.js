import React from 'react';
import { PaperProvider } from 'react-native-paper';
import AppNavigation from './src/navigation/AppNavigation';
export default function App() { return <PaperProvider><AppNavigation /></PaperProvider>; }
