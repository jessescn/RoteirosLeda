package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

   protected SkipListNode<T> root;
   protected SkipListNode<T> NIL;

   protected int maxHeight;

   protected double PROBABILITY = 0.5;

   public SkipListImpl(int maxHeight) {
      this.maxHeight = maxHeight;
      root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
      NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
      connectRootToNil();
   }

   /**
    * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
    * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve conectar
    * todos os forward. Senao o ROOT eh inicializado com level=1 e o metodo deve
    * conectar apenas o forward[0].
    */
   private void connectRootToNil() {
      for (int i = 0; i < maxHeight; i++) {
         root.forward[i] = NIL;
      }
   }

   @Override
   public void insert(int key, T newValue, int height) {
      SkipListNode<T> aux = this.root;
      SkipListNode<T> newNode = search(key);

      if (search(key) == null) {

         if (height > maxHeight) {
            RuntimeException e = new RuntimeException("the given height is greater than maxHeight");
            throw e;
         }

         newNode = new SkipListNode<T>(key, height, newValue);
         for (int level = maxHeight - 1; level >= 0; level--) {

            while (aux.getForward(level).getKey() < key) {
               aux = aux.getForward(level);
            }
            if (level + 1 <= height) {
               newNode.forward[level] = aux.getForward(level);
               aux.forward[level] = newNode;
            }
         }
      } else {
         newNode.setValue(newValue);
      }

   }

   @Override
   public void remove(int key) {
      SkipListNode<T> toBeRemoved = search(key);

      if (toBeRemoved != null) {
         SkipListNode<T> aux = this.root;

         for (int i = toBeRemoved.height() - 1; i >= 0; i--) {
            while (aux.getForward(i).getKey() != key) {
               aux = aux.getForward(i);
            }

            aux.forward[i] = toBeRemoved.forward[i];
         }
      }
   }

   @Override
   public int height() {

      int max = maxHeight - 1;

      SkipListNode<T> aux = this.root;

      while (aux.getForward(max).equals(NIL) && max > 0) {
         max--;
      }

      return max;

   }

   @Override
   public SkipListNode<T> search(int key) {

      SkipListNode<T> aux = this.root;

      for (int level = this.height() - 1; level >= 0; level--) {
         while (aux.forward[level].key < key) {
            aux = aux.forward[level];
         }
      }
      aux = aux.forward[0];
      if (aux.getKey() != key) {
         aux = null;
      }
      return aux;
   }

   @Override
   public int size() {
      SkipListNode<T> aux = this.root;

      int size = 0;
      while (!aux.getForward(0).equals(NIL)) {
         size++;
         aux = aux.getForward(0);
      }
      return size;
   }

   @Override
   public SkipListNode<T>[] toArray() {
      SkipListNode<T>[] array = new SkipListNode[this.size() + 2];
      SkipListNode<T> aux = this.root;
      int i = 0;
      while (!aux.equals(NIL)) {
         array[i++] = aux;
         aux = aux.getForward(0);

      }

      array[i++] = NIL;
      return array;
   }

}
