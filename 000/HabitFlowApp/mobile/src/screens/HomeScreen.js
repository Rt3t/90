import React, { useCallback, useEffect, useState } from 'react';
import { FlatList, RefreshControl, StyleSheet, View } from 'react-native';
import { Button, Card, Dialog, Portal, Text, TextInput, FAB } from 'react-native-paper';
import api from '../services/api';
import { useHabitStore } from '../store/habitStore';

const USER_ID = 1;

export default function HomeScreen() {
  const { habits, setHabits, loading, setLoading, error, setError, addHabit } = useHabitStore();
  const [dialog, setDialog] = useState(false);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  const loadHabits = useCallback(async () => {
    setLoading(true);
    try { const r = await api.get(`/habits/${USER_ID}`); setHabits(r.data); }
    catch (e) { setError(e?.message || 'Не удалось подключиться к серверу'); }
    finally { setLoading(false); }
  }, [setHabits, setLoading, setError]);

  useEffect(() => { loadHabits(); }, [loadHabits]);

  const createHabit = async () => {
    if (!title.trim()) return;
    try {
      const r = await api.post(`/habits?userId=${USER_ID}`, { title: title.trim(), description: description.trim() });
      addHabit(r.data); setTitle(''); setDescription(''); setDialog(false);
    } catch (e) { setError(e?.message || 'Не удалось создать привычку'); }
  };

  const completeHabit = async (id) => {
    try { await api.put(`/habits/${id}/complete`); await loadHabits(); }
    catch (e) { setError(e?.message || 'Не удалось обновить серию'); }
  };

  return <View style={styles.container}>
    <Text variant="headlineMedium" style={styles.heading}>Мои привычки</Text>
    {error ? <Card style={styles.error}><Card.Content><Text>{error}</Text><Button onPress={loadHabits}>Повторить</Button></Card.Content></Card> : null}
    <FlatList
      data={habits}
      refreshControl={<RefreshControl refreshing={loading} onRefresh={loadHabits} />}
      contentContainerStyle={habits.length ? styles.list : styles.emptyList}
      keyExtractor={(item) => String(item.id)}
      ListEmptyComponent={<Text>Пока нет привычек. Добавьте первую через +</Text>}
      renderItem={({ item }) => <Card style={styles.card}>
        <Card.Content>
          <Text variant="titleLarge">{item.title}</Text>
          {!!item.description && <Text style={styles.description}>{item.description}</Text>}
          <Text>Серия: {item.streak} 🔥</Text>
          <Button mode="contained" style={styles.button} onPress={() => completeHabit(item.id)}>Выполнено</Button>
        </Card.Content>
      </Card>}
    />
    <FAB icon="plus" style={styles.fab} onPress={() => setDialog(true)} />
    <Portal><Dialog visible={dialog} onDismiss={() => setDialog(false)}>
      <Dialog.Title>Новая привычка</Dialog.Title>
      <Dialog.Content>
        <TextInput label="Название" value={title} onChangeText={setTitle} style={styles.input} />
        <TextInput label="Описание" value={description} onChangeText={setDescription} multiline style={styles.input} />
      </Dialog.Content>
      <Dialog.Actions><Button onPress={() => setDialog(false)}>Отмена</Button><Button onPress={createHabit}>Создать</Button></Dialog.Actions>
    </Dialog></Portal>
  </View>;
}
const styles = StyleSheet.create({
  container:{flex:1,padding:16}, heading:{marginBottom:12}, list:{paddingBottom:100}, emptyList:{flexGrow:1,justifyContent:'center',alignItems:'center'},
  card:{marginBottom:12}, description:{marginVertical:8}, button:{marginTop:12}, fab:{position:'absolute',right:20,bottom:20}, error:{marginBottom:12}, input:{marginBottom:10}
});
