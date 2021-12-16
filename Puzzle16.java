import java.util.ArrayList;
import java.util.List;

public class Puzzle16 {
    public static void main(String[] args) {
        Util.init();
        String data = Util.load("puzzle16").get(0);

        StringBuilder bs = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            String bin = Integer.toBinaryString(Integer.parseInt(data.substring(i, i + 1), 16));
            bs.append("0000".substring(bin.length())).append(bin);
        }
        String binary = bs.toString();

        Pointer pointer = new Pointer();
        Packet packet = getPacket(binary, pointer);
        System.out.println("Sum of versions is " + packet.getSumOfVersionNums());
        System.out.println("Value of packet is " + packet.getValue());


        Util.end();
    }

    public static class Pointer {
        public int pointer = 0;
    }

    public static class Packet {
        public int version;
        public int typeID;
        public long value = -1;
        public List<Packet> subpackets = new ArrayList<>();

        public Packet(int version, int typeID) {
            this.version = version;
            this.typeID = typeID;
        }

        public int getSumOfVersionNums() {
            int sum = version;
            for (Packet p : subpackets) {
                sum += p.getSumOfVersionNums();
            }
            return sum;
        }

        public long getValue() {
            switch (typeID) {
                case 0:
                    long sum = 0;
                    for (Packet p : subpackets) {
                        sum += p.getValue();
                    }
                    return sum;
                case 1:
                    long product = 1;
                    for (Packet p : subpackets) {
                        product *= p.getValue();
                    }
                    return product;
                case 2:
                    long min = Long.MAX_VALUE;
                    for (Packet p : subpackets) {
                        long val = p.getValue();
                        if (val < min) min = val;
                    }
                    return min;
                case 3:
                    long max = Long.MIN_VALUE;
                    for (Packet p : subpackets) {
                        long val = p.getValue();
                        if (val > max) max = val;
                    }
                    return max;
                case 4:
                    return value;
                case 5:
                    return subpackets.get(0).getValue() > subpackets.get(1).getValue() ? 1 : 0;
                case 6:
                    return subpackets.get(0).getValue() < subpackets.get(1).getValue() ? 1 : 0;
                case 7:
                    return subpackets.get(0).getValue() == subpackets.get(1).getValue() ? 1 : 0;
            }
            return -1;
        }

        public void addPacket(Packet packet) {
            subpackets.add(packet);
        }
    }

    public static Packet getPacket(String binary, Pointer pointer) {
        int version = getNextNBits(binary, pointer, 3);
        int typeID = getNextNBits(binary, pointer, 3);
        Packet packet = new Packet(version, typeID);
        if (typeID == 4) {
            long value = 0;
            while (true) {
                long group = getNextNBits(binary, pointer, 5);
                value <<= 4;
                value += (group & 0b1111);
                if ((group & 0b10000) == 0) break;
            }
            packet.value = value;
        } else {
            int lengthTypeID = getNextNBits(binary, pointer, 1);
            if (lengthTypeID == 0) {
                int totalLength = getNextNBits(binary, pointer, 15);
                int startPointer = pointer.pointer;
                while (pointer.pointer - startPointer < totalLength) {
                    packet.addPacket(getPacket(binary, pointer));
                }
            } else {
                int numSubpackets = getNextNBits(binary, pointer, 11);
                for (int i = 0; i < numSubpackets; i++) {
                    packet.addPacket(getPacket(binary, pointer));
                }
            }
        }
        return packet;
    }

    public static int getNextNBits(String binary, Pointer pointer, int n) {
        int res = Integer.parseInt(binary.substring(pointer.pointer, pointer.pointer + n), 2);
        pointer.pointer += n;
        return res;
    }
}