import { create } from 'zustand';
export const useHabitStore = create((set) => ({
  habits: [], loading: false, error: null,
  setHabits: (habits) => set({ habits, error: null }),
  setLoading: (loading) => set({ loading }),
  setError: (error) => set({ error }),
  addHabit: (habit) => set((state) => ({ habits: [habit, ...state.habits] }))
}));
