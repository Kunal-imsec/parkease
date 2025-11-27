import React, { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { useInView } from 'react-intersection-observer';
import { useNavigate } from 'react-router-dom';
import './LandingPage.css';

const LandingPage = () => {
    const navigate = useNavigate();
    const [ref, inView] = useInView({ threshold: 0.1, triggerOnce: true });
    const [scrollY, setScrollY] = useState(0);

    useEffect(() => {
        const handleScroll = () => setScrollY(window.scrollY);
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    const handleGetStarted = () => {
        localStorage.setItem('hasVisited', 'true');
        navigate('/register');
    };

    const handleLogin = () => {
        localStorage.setItem('hasVisited', 'true');
        navigate('/login');
    };

    const features = [
        {
            icon: '🚗',
            title: 'Real-Time Availability',
            description: 'See available parking spots instantly with live updates'
        },
        {
            icon: '⚡',
            title: 'Instant Booking',
            description: 'Reserve your spot in seconds with one-click booking'
        },
        {
            icon: '🗺️',
            title: 'Smart Navigation',
            description: 'Google Maps integration for seamless navigation to your spot'
        },
        {
            icon: '🎯',
            title: 'Auto-Complete',
            description: 'Bookings automatically complete when time expires'
        },
        {
            icon: '🔒',
            title: 'Secure Payments',
            description: 'Safe and encrypted payment processing'
        },
        {
            icon: '📱',
            title: 'Mobile Friendly',
            description: 'Book parking on the go from any device'
        }
    ];

    return (
        <div className="landing-page">
            {/* Animated Car Background */}
            <div className="car-animation-container">
                <div className="car car-1"></div>
                <div className="car car-2"></div>
                <div className="car car-3"></div>
                <div className="grid-overlay"></div>
            </div>

            {/* Hero Section */}
            <section className="hero-section">
                <motion.div
                    className="hero-content"
                    initial={{ opacity: 0, y: 30 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.8 }}
                >
                    <motion.h1
                        className="hero-title"
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ delay: 0.2, duration: 0.8 }}
                    >
                        Park Smarter,
                        <br />
                        <span className="gradient-text">Drive Faster</span>
                    </motion.h1>

                    <motion.p
                        className="hero-subtitle"
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        transition={{ delay: 0.4, duration: 0.8 }}
                    >
                        Find, book, and navigate to parking spots in seconds.
                        <br />
                        The future of urban parking is here.
                    </motion.p>

                    <motion.div
                        className="hero-cta"
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ delay: 0.6, duration: 0.8 }}
                    >
                        <button className="cta-primary" onClick={handleGetStarted}>
                            Get Started
                            <svg className="arrow-icon" viewBox="0 0 20 20" fill="currentColor">
                                <path fillRule="evenodd" d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z" clipRule="evenodd" />
                            </svg>
                        </button>
                        <button className="cta-secondary" onClick={handleLogin}>
                            Sign In
                        </button>
                    </motion.div>

                    <motion.div
                        className="scroll-indicator"
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 1 }}
                        transition={{ delay: 1, duration: 1 }}
                    >
                        <span>Scroll to explore</span>
                        <svg className="scroll-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 14l-7 7m0 0l-7-7m7 7V3" />
                        </svg>
                    </motion.div>
                </motion.div>
            </section>

            {/* Features Section */}
            <section className="features-section" ref={ref}>
                <motion.div
                    className="features-header"
                    initial={{ opacity: 0, y: 30 }}
                    animate={inView ? { opacity: 1, y: 0 } : {}}
                    transition={{ duration: 0.8 }}
                >
                    <h2 className="section-title">Why Choose ParkEase?</h2>
                    <p className="section-subtitle">
                        Experience the fastest, smartest way to find parking
                    </p>
                </motion.div>

                <div className="features-grid">
                    {features.map((feature, index) => (
                        <motion.div
                            key={index}
                            className="feature-card"
                            initial={{ opacity: 0, y: 30 }}
                            animate={inView ? { opacity: 1, y: 0 } : {}}
                            transition={{ delay: index * 0.1, duration: 0.6 }}
                            whileHover={{ y: -5, transition: { duration: 0.2 } }}
                        >
                            <div className="feature-icon">{feature.icon}</div>
                            <h3 className="feature-title">{feature.title}</h3>
                            <p className="feature-description">{feature.description}</p>
                        </motion.div>
                    ))}
                </div>
            </section>

            {/* How It Works Section */}
            <section className="how-it-works-section">
                <motion.div
                    className="how-it-works-content"
                    initial={{ opacity: 0 }}
                    whileInView={{ opacity: 1 }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.8 }}
                >
                    <h2 className="section-title">How It Works</h2>
                    <div className="steps-container">
                        <div className="step">
                            <div className="step-number">01</div>
                            <h3>Search</h3>
                            <p>Find available parking spots near your destination</p>
                        </div>
                        <div className="step-connector"></div>
                        <div className="step">
                            <div className="step-number">02</div>
                            <h3>Book</h3>
                            <p>Reserve your spot instantly with one click</p>
                        </div>
                        <div className="step-connector"></div>
                        <div className="step">
                            <div className="step-number">03</div>
                            <h3>Navigate</h3>
                            <p>Get directions and park with confidence</p>
                        </div>
                    </div>
                </motion.div>
            </section>

            {/* Final CTA Section */}
            <section className="final-cta-section">
                <motion.div
                    className="final-cta-content"
                    initial={{ opacity: 0, scale: 0.95 }}
                    whileInView={{ opacity: 1, scale: 1 }}
                    viewport={{ once: true }}
                    transition={{ duration: 0.8 }}
                >
                    <h2 className="final-cta-title">Ready to Park Smarter?</h2>
                    <p className="final-cta-subtitle">Join thousands of drivers saving time every day</p>
                    <div className="final-cta-buttons">
                        <button className="cta-primary" onClick={handleGetStarted}>
                            Create Account
                        </button>
                        <button className="cta-secondary" onClick={handleLogin}>
                            Sign In
                        </button>
                    </div>
                </motion.div>
            </section>

            {/* Footer */}
            <footer className="landing-footer">
                <p>© 2025 ParkEase. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default LandingPage;
