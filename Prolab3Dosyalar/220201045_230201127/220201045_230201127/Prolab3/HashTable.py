class HashNode:
    def __init__(self, anahtar, deger):
        self.anahtar = anahtar
        self.deger = deger
        self.sonraki = None


class HashTable:
    def __init__(self, kapasite):
        self.kapasite = kapasite
        self.boyut = 0
        self.table = [None] * kapasite

    def _hash(self, anahtar):
        p = 31
        m = 10**9 + 9
        hash_value = 0
        p_pow = 1

        for char in anahtar:
            hash_value = (hash_value + (ord(char) - ord('a') + 1) * p_pow) % m
            p_pow = (p_pow * p) % m
        return hash_value % self.kapasite

    def insert(self, anahtar, deger):
        index = self._hash(anahtar)

        if self.table[index] is None:
            self.table[index] = HashNode(anahtar, deger)
            self.boyut += 1
        else:
            current = self.table[index]
            while current:
                if current.anahtar == anahtar:
                    current.deger = deger
                    return
                current = current.sonraki
            new_node = HashNode(anahtar, deger)
            new_node.sonraki = self.table[index]
            self.table[index] = new_node
            self.boyut += 1

    def search(self, anahtar):
        index = self._hash(anahtar)

        current = self.table[index]
        while current:
            if current.anahtar == anahtar:
                return current.deger
            current = current.sonraki

        raise KeyError(anahtar)

    def remove(self, anahtar):
        index = self._hash(anahtar)

        previous = None
        current = self.table[index]

        while current:
            if current.anahtar == anahtar:
                if previous:
                    previous.sonraki = current.sonraki
                else:
                    self.table[index] = current.sonraki
                self.boyut -= 1
                return
            previous = current
            current = current.sonraki

        raise KeyError(anahtar)

    def __len__(self):
        return self.boyut

    def __contains__(self, anahtar):
        try:
            self.search(anahtar)
            return True
        except KeyError:
            return False
