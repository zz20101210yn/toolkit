/**
 * Copyright 2011-2012 www.wanlitong.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paic.jrkj.tk.tools.list;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2016-1-8 22:23:55 $
 * @serial 1.0
 * @since 2016-1-8 22:23:55
 */
public final class DLinkList<E> {

    private final LinkNode<E> first = new LinkNode<E>(null);
    private final LinkNode<E> last = new LinkNode<E>(null);
    private volatile int size = 0;

    public DLinkList() {
        first.setNext(last);
        last.setPrev(first);
    }

    public E popFirst() {
        synchronized (this) {
            if (size > 0) {
                LinkNode<E> current = first.getNext();
                LinkNode<E> next = current.getNext();
                first.setNext(next);
                next.setPrev(first);
                current.setNext(null);
                current.setPrev(null);
                size--;
                return current.getValue();
            }
            return null;
        }
    }

    public void addLast(E data) {
        LinkNode<E> current = new LinkNode<E>(data);
        synchronized (this) {
            LinkNode<E> prev = last.getPrev();
            prev.setNext(current);
            current.setPrev(prev);

            current.setNext(last);
            last.setPrev(current);
            size++;
        }
    }

    public int size() {
        return size;
    }
}
